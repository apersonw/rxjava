package top.rxjava.third.weixin.mp.api;

import org.apache.commons.lang3.StringUtils;
import top.rxjava.third.weixin.common.api.WxErrorExceptionHandler;
import top.rxjava.third.weixin.common.api.WxMessageDuplicateChecker;
import top.rxjava.third.weixin.common.api.WxMessageInMemoryDuplicateChecker;
import top.rxjava.third.weixin.common.session.InternalSession;
import top.rxjava.third.weixin.common.session.InternalSessionManager;
import top.rxjava.third.weixin.common.session.StandardSessionManager;
import top.rxjava.third.weixin.common.session.WxSessionManager;
import top.rxjava.third.weixin.common.util.LogExceptionHandler;
import top.rxjava.third.weixin.mp.bean.message.WxMpXmlMessage;
import top.rxjava.third.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 * <p>
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link WxMpMessageRouterRule#next()}
 * 3. 规则的结束必须用{@link WxMpMessageRouterRule#end()}或者{@link WxMpMessageRouterRule#next()}，否则不会生效
 * <p>
 * 使用方法：
 * WxMpMessageRouter router = new WxMpMessageRouter();
 * router
 * .rule()
 * .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 * .interceptor(interceptor, ...).handler(handler, ...)
 * .end()
 * .rule()
 * // 另外一个匹配规则
 * .end()
 * ;
 * <p>
 * // 将WxXmlMessage交给消息路由器
 * router.route(message);
 */
public class WxMpMessageRouter {
    private static final int DEFAULT_THREAD_POOL_SIZE = 100;
    protected final Logger log = LoggerFactory.getLogger(WxMpMessageRouter.class);
    private final List<WxMpMessageRouterRule> rules = new ArrayList<>();

    private final WxMpService wxMpService;

    private ExecutorService executorService;

    private WxMessageDuplicateChecker messageDuplicateChecker;

    private WxSessionManager sessionManager;

    private WxErrorExceptionHandler exceptionHandler;

    public WxMpMessageRouter(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
        this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
        this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
        this.sessionManager = new StandardSessionManager();
        this.exceptionHandler = new LogExceptionHandler();
    }

    /**
     * 使用自定义的 {@link ExecutorService}.
     */
    public WxMpMessageRouter(WxMpService wxMpService, ExecutorService executorService) {
        this.wxMpService = wxMpService;
        this.executorService = executorService;
        this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
        this.sessionManager = new StandardSessionManager();
        this.exceptionHandler = new LogExceptionHandler();
    }

    /**
     * 如果使用默认的 {@link ExecutorService}，则系统退出前，应该调用该方法.
     */
    public void shutDownExecutorService() {
        this.executorService.shutdown();
    }


    /**
     * 设置自定义的 {@link ExecutorService}
     * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * 设置自定义的 {@link top.rxjava.third.weixin.common.api.WxMessageDuplicateChecker}
     * 如果不调用该方法，默认使用 {@link top.rxjava.third.weixin.common.api.WxMessageInMemoryDuplicateChecker}
     */
    public void setMessageDuplicateChecker(WxMessageDuplicateChecker messageDuplicateChecker) {
        this.messageDuplicateChecker = messageDuplicateChecker;
    }

    /**
     * 设置自定义的{@link top.rxjava.third.weixin.common.session.WxSessionManager}
     * 如果不调用该方法，默认使用 {@link top.rxjava.third.weixin.common.session.StandardSessionManager}
     */
    public void setSessionManager(WxSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * 设置自定义的{@link top.rxjava.third.weixin.common.api.WxErrorExceptionHandler}
     * 如果不调用该方法，默认使用 {@link top.rxjava.third.weixin.common.util.LogExceptionHandler}
     */
    public void setExceptionHandler(WxErrorExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    List<WxMpMessageRouterRule> getRules() {
        return this.rules;
    }

    /**
     * 开始一个新的Route规则.
     */
    public WxMpMessageRouterRule rule() {
        return new WxMpMessageRouterRule(this);
    }

    /**
     * 处理微信消息.
     */
    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, final Map<String, Object> context) {
        return route(wxMessage, context, null);
    }

    /**
     * 处理不同appid微信消息
     */
    public WxMpXmlOutMessage route(final String appid, final WxMpXmlMessage wxMessage, final Map<String, Object> context) {
        return route(wxMessage, context, this.wxMpService.switchoverTo(appid));
    }

    /**
     * 处理微信消息.
     */
    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, final Map<String, Object> context, WxMpService wxMpService) {
        if (wxMpService == null) {
            wxMpService = this.wxMpService;
        }
        final WxMpService mpService = wxMpService;
        if (isMsgDuplicated(wxMessage)) {
            // 如果是重复消息，那么就不做处理
            return null;
        }

        final List<WxMpMessageRouterRule> matchRules = new ArrayList<>();
        // 收集匹配的规则
        for (final WxMpMessageRouterRule rule : this.rules) {
            if (rule.test(wxMessage)) {
                matchRules.add(rule);
                if (!rule.isReEnter()) {
                    break;
                }
            }
        }

        if (matchRules.size() == 0) {
            return null;
        }

        WxMpXmlOutMessage res = null;
        final List<Future<?>> futures = new ArrayList<>();
        for (final WxMpMessageRouterRule rule : matchRules) {
            // 返回最后一个非异步的rule的执行结果
            if (rule.isAsync()) {
                futures.add(
                        this.executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                rule.service(wxMessage, context, mpService, WxMpMessageRouter.this.sessionManager, WxMpMessageRouter.this.exceptionHandler);
                            }
                        })
                );
            } else {
                res = rule.service(wxMessage, context, mpService, this.sessionManager, this.exceptionHandler);
                // 在同步操作结束，session访问结束
                this.log.debug("End session access: async=false, sessionId={}", wxMessage.getFromUser());
                sessionEndAccess(wxMessage);
            }
        }

        if (futures.size() > 0) {
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (Future<?> future : futures) {
                        try {
                            future.get();
                            WxMpMessageRouter.this.log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUser());
                            // 异步操作结束，session访问结束
                            sessionEndAccess(wxMessage);
                        } catch (InterruptedException e) {
                            WxMpMessageRouter.this.log.error("Error happened when wait task finish", e);
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException e) {
                            WxMpMessageRouter.this.log.error("Error happened when wait task finish", e);
                        }
                    }
                }
            });
        }
        return res;
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage) {
        return this.route(wxMessage, new HashMap<String, Object>(2));
    }

    public WxMpXmlOutMessage route(String appid, final WxMpXmlMessage wxMessage) {
        return this.route(appid, wxMessage, new HashMap<String, Object>(2));
    }

    private boolean isMsgDuplicated(WxMpXmlMessage wxMessage) {
        StringBuilder messageId = new StringBuilder();
        if (wxMessage.getMsgId() == null) {
            messageId.append(wxMessage.getCreateTime())
                    .append("-").append(wxMessage.getFromUser())
                    .append("-").append(StringUtils.trimToEmpty(wxMessage.getEventKey()))
                    .append("-").append(StringUtils.trimToEmpty(wxMessage.getEvent()));
        } else {
            messageId.append(wxMessage.getMsgId())
                    .append("-").append(wxMessage.getCreateTime())
                    .append("-").append(wxMessage.getFromUser());
        }

        if (StringUtils.isNotEmpty(wxMessage.getUserCardCode())) {
            messageId.append("-").append(wxMessage.getUserCardCode());
        }

        return this.messageDuplicateChecker.isDuplicate(messageId.toString());

    }

    /**
     * 对session的访问结束.
     */
    private void sessionEndAccess(WxMpXmlMessage wxMessage) {
        InternalSession session = ((InternalSessionManager) this.sessionManager).findSession(wxMessage.getFromUser());
        if (session != null) {
            session.endAccess();
        }
    }
}

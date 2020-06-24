package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaLiveService;
import org.rxjava.third.tencent.miniapp.api.WxMaService;
import org.rxjava.third.tencent.miniapp.bean.WxMaGetLiveInfo;
import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.common.WxType;
import org.rxjava.third.tencent.common.error.WxError;
import org.rxjava.third.tencent.common.error.WxErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  Created by yjwang on 2020/4/5.
 * </pre>
 *
 * @author <a href="https://github.com/yjwang3300300">yjwang</a>
 */
@AllArgsConstructor
public class WxMaLiveServiceImpl implements WxMaLiveService {
  private static final JsonParser JSON_PARSER = new JsonParser();
  private WxMaService service;

  @Override
  public WxMaGetLiveInfo getLiveInfo(Integer start, Integer limit) throws WxErrorException {
    JsonObject jsonObject = getJsonObject(start, limit, null);
    return WxMaGetLiveInfo.fromJson(jsonObject.toString());
  }

  @Override
  public List<WxMaGetLiveInfo.RoomInfo> getLiveinfos() throws WxErrorException {
    List<WxMaGetLiveInfo.RoomInfo> results = new ArrayList<>();
    Integer start = 0;
    Integer limit = 80;
    Integer tatal = 0;
    WxMaGetLiveInfo liveInfo = null;
    do {
      if (tatal != 0 && tatal <= start) {
        break;
      }
      liveInfo = getLiveInfo(start, limit);
      if (liveInfo == null) {
        return null;
      }
      results.addAll(liveInfo.getRoomInfos());
      tatal = liveInfo.getTotal();
      start = results.size();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (results.size() <= tatal);
    return results;
  }

  @Override
  public WxMaGetLiveInfo getLiveReplay(String action, Integer room_id, Integer start, Integer limit) throws WxErrorException {
    Map<String, Object> map = new HashMap(4);
    map.put("action", action);
    map.put("room_id", room_id);
    JsonObject jsonObject = getJsonObject(start, limit, map);
    return WxMaGetLiveInfo.fromJson(jsonObject.toString());
  }

  @Override
  public WxMaGetLiveInfo getLiveReplay(Integer room_id, Integer start, Integer limit) throws WxErrorException {
    return getLiveReplay("get_replay", room_id, start, limit);
  }

  /**
   * 包装一下
   *
   * @param start
   * @param limit
   * @param map
   * @return
   * @throws WxErrorException
   */
  private JsonObject getJsonObject(Integer start, Integer limit, Map<String, Object> map) throws WxErrorException {
    if (map == null) {
      map = new HashMap(2);
    }
    map.put("start", start);
    map.put("limit", limit);
    String responseContent = service.post(GET_LIVE_INFO, WxMaGsonBuilder.create().toJson(map));
    JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
    if (jsonObject.get("errcode").getAsInt() != 0) {
      throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
    }
    return jsonObject;
  }
}

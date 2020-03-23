package org.rxjava.service.example.person;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.example.entity.Goods;
import org.rxjava.service.example.entity.Group;
import org.rxjava.service.example.entity.GroupGoods;
import org.rxjava.service.example.form.GroupGoodsListForm;
import org.rxjava.service.example.form.SearchGoodsListForm;
import org.rxjava.service.example.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * 商品控制器
 */
@RestController
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 搜索商品列表
     */
    @GetMapping("goodsList")
    public Flux<Goods> searchGoodsList(
            @Valid SearchGoodsListForm form
    ) {
        return goodsService.searchGoodsList(form);
    }

    /**
     * 查询分组商品列表
     */
    @Login(false)
    @GetMapping("group/groupGoodsList")
    public Flux<GroupGoods> findGroupGoodsList(
            @Valid GroupGoodsListForm form
    ) {
        return goodsService.findGroupGoodsList(form);
    }

    /**
     * 查询商品
     */
    @Login(false)
    @GetMapping("goods/{goodsId}")
    public Mono<Goods> findGoods(
            @PathVariable String goodsId
    ) {
        return goodsService.findGoods(goodsId);
    }

    /**
     * 查询指定类型分组列表
     */
    @Login(false)
    @GetMapping("groupCategory/{groupCategoryId}/groupList")
    public Flux<Group> findGroupList(
            @PathVariable String groupCategoryId
    ) {
        return goodsService.findGroupList(groupCategoryId);
    }

    /**
     * 查询分组
     */
    @Login(false)
    @GetMapping("group/{groupId}")
    public Mono<Group> findGroup(
            @PathVariable String groupId
    ) {
        return goodsService.findGroup(groupId);
    }
}

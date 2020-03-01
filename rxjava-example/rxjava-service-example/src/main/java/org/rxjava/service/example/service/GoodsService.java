package org.rxjava.service.example.service;

import org.rxjava.service.example.entity.Goods;
import org.rxjava.service.example.entity.Group;
import org.rxjava.service.example.entity.GroupGoods;
import org.rxjava.service.example.form.GroupGoodsListForm;
import org.rxjava.service.example.form.SearchGoodsListForm;
import org.rxjava.service.example.repository.GoodsRepository;
import org.rxjava.service.example.repository.GroupGoodsRepository;
import org.rxjava.service.example.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
public class GoodsService {
    @Autowired
    private GroupGoodsRepository groupGoodsRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GroupRepository groupRepository;

    /**
     * 查询分组商品列表
     */
    public Flux<GroupGoods> findGroupGoodsList(@Valid GroupGoodsListForm form) {
        return groupGoodsRepository
                .findList(form.getGroupId(), PageRequest.of(form.getPage(), form.getPageSize()));
    }

    /**
     * 查询商品
     */
    public Mono<Goods> findGoods(String goodsId) {
        return goodsRepository.findById(goodsId);
    }

    /**
     * 搜索商品列表
     */
    public Flux<Goods> searchGoodsList(SearchGoodsListForm form) {
        return goodsRepository.findFlux();
    }

    /**
     * 查询指定类型分组列表
     */
    public Flux<Group> findGroupList(String groupCategoryId) {
        return groupRepository
                .findAllByGroupCategoryId(groupCategoryId);
    }

    /**
     * 查询分组
     */
    public Mono<Group> findGroup(String groupId) {
        return groupRepository.findById(groupId);
    }
}

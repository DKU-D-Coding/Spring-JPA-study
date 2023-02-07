package com.dku.springstudy.item.repository;

import com.dku.springstudy.item.entity.Category;
import com.dku.springstudy.item.entity.Item;
import com.dku.springstudy.item.entity.Status;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dku.springstudy.item.entity.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public Page<Item> findItemByParam(String query, String category, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder(item.category.eq(Category.valueOf(category)))
                .and(item.status.ne(Status.SOLD));

        if (query != null) {
            builder.and(item.title.contains(query));
        }

        List<Item> items = queryFactory
                .selectFrom(item)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryFactory
                .selectFrom(item)
                .where(builder);

        return PageableExecutionUtils.getPage(items, pageable, () -> countQuery.fetch().size());

    }
}

package com.platform.service.impl;

import com.platform.entity.AnnInfoEs;
import com.platform.entity.TAnnInfo;
import com.platform.service.IAnnEsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class AnnEsServiceImpl implements IAnnEsService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public String save(TAnnInfo tAnnInfo){
        if (tAnnInfo == null) {
            return null;
        }
        AnnInfoEs annInfoEs = new AnnInfoEs();
        BeanUtils.copyProperties(tAnnInfo, annInfoEs);
        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(annInfoEs.getId())).withObject(annInfoEs).build();
        return elasticsearchTemplate.index(indexQuery);
    }

}

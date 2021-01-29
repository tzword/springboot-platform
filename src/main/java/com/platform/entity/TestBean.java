package com.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author jianghy
 * @Description: 测试实体
 * @date 2020/11/13 9:49
 */
@Data
@Document(indexName = "testdoct", type = "testbean")
public class TestBean implements Serializable {

    public TestBean() {
    }

    public TestBean(long id, String name, Integer age, String sex, String desc) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.desc = desc;
    }

    @Id
    private long id;
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String name;
    private Integer age;
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String sex;
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String desc;
}

package com.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianghy
 * @Description: 城市信息
 * @date 2020/12/23 16:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityInfo {
    /** 城市 */
    private String city;

    /** 经度 */
    private Double longitude;

    /** 纬度 */
    private Double latitude;
}

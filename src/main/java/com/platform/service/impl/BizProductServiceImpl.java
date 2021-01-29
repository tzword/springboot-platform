package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.entity.BizProduct;
import com.platform.mapper.BizProductMapper;
import com.platform.service.IBizProductService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianghy
 * @since 2021-01-11
 */
@Slf4j
@Service
public class BizProductServiceImpl extends ServiceImpl<BizProductMapper, BizProduct> implements IBizProductService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BizProductMapper bizProductMapper;

    
    /**
     * @Description: 分布式锁秒杀
     * @param productId 1 
     * @return boolean 
     * @throws
     * @author jianghy
     * @date 2021/1/11 16:46
     */
    public boolean decrementProductStore(String productId) {
        String key = "dec_store_lock_" + productId;
        RLock lock = redissonClient.getLock(key);
        try {
            //加锁
            lock.lock();
            //查询操作
            BizProduct bizProduct = bizProductMapper.selectById(productId);
            if (bizProduct.getProductStock() == 0) {
                return false;
            }

            bizProduct.setProductStock(bizProduct.getProductStock() - 1);
            bizProductMapper.updateById(bizProduct);
            log.info("卖出成功-----------");
        } catch (Exception e){
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return true;
    }
}

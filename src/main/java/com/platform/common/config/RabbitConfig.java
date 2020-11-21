package com.platform.common.config;

import lombok.Value;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description RabbitConfig配置
 * @Author jianghy
 * @Date 2020/5/30 10:48
 */
@Configuration
public class RabbitConfig {
    //订单交换机
    public static final String orderExchange = "order-exchange";
    //订单队列
    public static final String orderQueue = "order-queue";
    //订单路由
    public static final String orderRoutingKey = "order-routing-key";
    //死信交换机
    public static final String dlxExchange = "dlx-exchange";
    //死信队列
    public static final String dlxQueue = "dlx-queue";
    //死信路由
    public static final String dlxRoutingKey = "order-routing-key";


    /**
     * 声明订单业务队列
     * @return Queue
     */
    @Bean
    public Queue orderQueue() {
        Map<String,Object> arguments = new HashMap<>(2);
        // 绑定该队列到私信交换机
        arguments.put("x-dead-letter-exchange",dlxExchange);
        arguments.put("x-dead-letter-routing-key",dlxRoutingKey);
        return new Queue(orderQueue,true,false,false,arguments);
    }

    /**
     * 声明订单业务交换机
     * @return DirectExchange
     */
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    /**
     * 绑定订单队列到订单交换机
     * @return Binding
     */
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(orderRoutingKey);

    }

    /**
     * 声明死信队列
     * @return DirectExchange
     */
    @Bean
    public TopicExchange dlxExchange() {
        return new TopicExchange(dlxExchange);
    }

    /**
     * 声明死信队列
     * @return Queue
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue(dlxQueue);
    }

    /**
     * 绑定死信队列到死信交换机
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(dlxQueue())
                .to(dlxExchange())
                .with(dlxRoutingKey);
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });

        return rabbitTemplate;
    }

}

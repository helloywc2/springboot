package com.example.demo.repository;

import com.example.demo.dataobj.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("1111");
        orderDetail.setProductId("11112");
        orderDetail.setProductIcon("asdf");
        orderDetail.setProductName("皮蛋皮蛋皮蛋");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);


        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception{
        List<OrderDetail> orderDetailList = repository.findByOrderId("111");
        Assert.assertNotEquals(0,orderDetailList.size());
    }

}

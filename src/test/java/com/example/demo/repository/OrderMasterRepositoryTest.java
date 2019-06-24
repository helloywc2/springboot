package com.example.demo.repository;

import com.example.demo.dataobj.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("买家名字");
        orderMaster.setBuyerPhone("131");
        orderMaster.setBuyerAddress("我来也");
        orderMaster.setBuyerOpenid("110");
        orderMaster.setOrderAmount(new BigDecimal(2.3));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception{

        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);
        System.out.println(result.getTotalElements());

    }

}

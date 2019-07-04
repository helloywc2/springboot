package com.example.demo.controller;

import com.example.demo.VO.ResultVO;
import com.example.demo.converter.OrderForm2OrderDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.enums.ResultEnum;
import com.example.demo.exception.SellException;
import com.example.demo.form.OrderForm;
import com.example.demo.service.BuyerService;
import com.example.demo.service.OrderService;
import com.example.demo.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private BuyerService buyerService;

    // 1 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>>create(@Valid OrderForm orderForm,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage()
            );
        }

        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

//        return ResultVOUtil.success(map);
        return ResultVOUtil.success(map);
    }

    // 2 订单列表

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
            ){

        if(StringUtils.isEmpty(openid)){
            log.error("【订单查询列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage =  orderService.findList(openid, request);

        // 转换 Date -> long 低效



        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    // 3 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId){


        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);


        return ResultVOUtil.success(orderDTO);
    }

    // 4 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId){

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}

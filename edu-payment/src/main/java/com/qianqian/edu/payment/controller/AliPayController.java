package com.qianqian.edu.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qianqian.edu.common.dto.form.OrderInfoForm;
import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.payment.service.PayOrderService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/17 17:51
 */
@Controller
@RequestMapping
@Slf4j
public class AliPayController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private PayOrderService payOrderService;

    private final String APP_ID = "2016101800718173";
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCji0BHk/iHJlIbfRVzx7xiL2Iz5FUfW4R26EEOyzFqzSX3IN4qlQza+27zTZQG0uZeSQKtzDt6v5EKNHsiIzeMqIdgF0NZUbHEOIwrmGnJkx52I8rqs13WNzFZtOIwTN+YKImBr6LhOieaYbQB3cuaTHhEfnA6QKn06wVk6v5+ljRzvv766r8ObZ2MYXvJdRwAP94Fx3l2L9+FO3mUZbhuKJQn2dU+DDkm4ISihRAWLLgAwfp4ZOrtllR937UvREJzLNj31XM//MLYV7lbYqTdG5tWyTPcWkKwVfSl3GbqMNRWtVFdrqpKkPvQoQt4Ba2EjmV240p1MpqDUdXHwBgJAgMBAAECggEACbhB9r8Vmzqs0JNrjfkF+jPkuz7LzFRNGCAPlch/xGAy7HAYpCE9pVoiKvWmYY1xQZNHRHsJ4e62uQuLcejz6Tku6ogXaZyQWyAdPI4PY3qJ6e2QpshIQG4BUdqNQt+evlVSbtG4dXhWWDeMUjwTWLVIxJFbLCshrru1E11gCLfeInplNcIL0TxANcsNHuZjYRubZdHC8/jOhzvVNNkJebiJoGmRIX/LgbKX0gsgq8/UVpOaaXOMqNeK9fCC5OoFIEuwEfURfQ5YmzatO5Ou+tannuNS8TiSwrbgyJ2eNZHvtcuNjFb7AERwwCYKlhb3K68v5bx1IJ2ZloGc2Ktc3QKBgQDmOQ/ObWQGNRy7iA+QLybfuQzCJ0yofclopJm5zLqHsxy+/aqRuoV/233XrkGiH4xTZB2zaK/4m23RMPrAZv2a5yIEpA8Ic0oYwQMmcQJgb2vdOa48z/acsXcPAGDrmCav6pfiQA/rpRbadBvimzdYmZrmaZ2IPi8xN+h7llXlQwKBgQC12vQXG2kKyjHUzZ6Pyaj71bFmTss2mWCU+hwI9ARsjnvORFSgLp0zk609YGKJTyAnMqFzwD9bUv/Xh0sJ2iiuXJnS1sGn2aga1sUAImEmnt9+pzKjJajbvW6bBAjeSlcWkmpgM/9nena2H+9jnMDXRieDMpTE+HX1BQGuK+FSwwKBgAiXrJ1Zu9f3d4EQTPRmrh6/e1hruxK2zyOWsR4cJAIYj0QZl5okygLBoGrCWDTC1KvwGqOtjpPKyUh7eYBg16pGYiCmNuFr3CIcYg+3PA15Wx9And4rLNsgTH9E1lk0roz2RoDtPyreWIICv1+vx1sJMJzU6EpDd+NwYL7sF3ydAoGAR3GsoYNYQi/rYdIiMBUmuUFbv3jHJz0cj4qvBulu74MOqpt3r7KAeenTjqBWNeGLHTsr6MCnY2Z5MqBpX4aAoveoSwep7xp4i4rCvGnFRxn1bdmb8DJ0Y1fJo0bzgPktu6Vr+z2SYoHfL2/eaYEE1c4u6cEuZy/Bj7AW1+lPTWcCgYBI5QZ7lmM2liLpq1RdueDDPz0keuNGDAdXRtVVo1lYIq/SJ4RMmVJbNDjqwTmVMvlhLksH8m6NcHyIEFqTSfwmBxcBZxTOnP9ybXJ3MdiKUOZilP6dtnAIVhqpHtFY1kcztG7njb6QdS59BHpq+dL6v3sDrgS6IuMaZvzZGLllOQ==";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAptLkali4E8MVjUK/nZ0nllxZNiguaKf1+dei1wG1Gc4tBcRrp+7PwTwbblXsqIfc2oIMBbLcbhRfBarSF4LpkacOYdOiIUiupt+k4QxFQEWaY6YyIsxCt9EOtKpZEmwP34x7Dk6mi4ngV21QBsoqzASauHP8R9yr8NpCrTVk63xIwaJTFGFRVVkJsgkPQ25JKo5YlRx9bEzG54licjhzeTCrdYNvXz8Px9iUhf+3fpsZwUM5iXpway0+PkO6cRbhcN6UdMy8XT2Va6U9wfAI+3CBWBig0N93czM9NI37KQ43SF3Q0w9nTsxawOntmKMQ1mleGcjjCgzMWSwmqr8kXQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://qianqianstudy.vipgz4.idcfengye.com/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://qianqianstudy.vipgz4.idcfengye.com/returnUrl";


    @GetMapping(value = "alipay")
    public String alipay(
            @ApiParam( name = "orderInfoForm",value = "订单表单",required = true)
            OrderInfoForm orderInfoForm) throws IOException {

//        OrderInfoForm orderInfoForm1 = JSON.parseObject(wholeStr, OrderInfoForm.class);
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);



        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderInfoForm.getId();
        //付款金额，必填
        String total_amount =orderInfoForm.getTotalPrice().toString();
        //订单名称，必填
        String subject =orderInfoForm.getCourseName();
        //商品描述，可空
        String body = orderInfoForm.getOrderDescription();

        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" +CHARSET );
        try {
            response.getWriter().write(form);// 直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public R returnUrl(HttpServletRequest request, HttpServletResponse httpResponse)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            EduOrder order = new EduOrder();
            order.setId(out_trade_no);
            order.setTransactionNumber(trade_no);
            //支付成功，修复支付状态
            R r = payOrderService.updateOrder(order);
            log.info("订单状态：已支付！");
            return r.message("ok");//跳转付款成功页面
        }else{
            return R.error().message("no");//跳转付款失败页面
        }

    }

    @RequestMapping(value = "/notifyUrl")
    public R test(){
      return   R.ok().message("这是支付宝异步通知！");
    }

}


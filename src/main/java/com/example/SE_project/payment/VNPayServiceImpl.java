package com.example.SE_project.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService{

    private VNPayConfig config;

    @Value(value = "${payment.pay.url}")
    private String payUrl;
    @Value(value = "${payment.return.url}")
    private String returnUrl;
    @Value(value = "${payment.TmnCode}")
    private String tmnCode;
    @Value(value = "${payment.secretKey}")
    private String secretKey;
    @Value(value = "${payment.api.url}")
    private String apiUrl;

    @Override
    public String pay(HttpServletRequest req, Integer numPages, String id) throws Exception{
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        Long amountLong = numPages.longValue()*500L*100L;
        String bankCode = "VNBANK";

        String vnp_TxnRef = config.getRandomNumber(8);
        String vnp_IpAddr = config.getIpAddress(req);

        String vnp_TmnCode = tmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountLong));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", String.valueOf(id));
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        // Tạo Map chứa tham số id
        Map<String, String> returnUrl_param = new HashMap<>();
        returnUrl_param.put("id", String.valueOf(id));
        returnUrl_param.put("numPages", String.valueOf(numPages));

        // Chuyển Map thành chuỗi query string
        String queryString = returnUrl_param.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        // Gán URL có chứa tham số id
        vnp_Params.put("vnp_ReturnUrl", returnUrl + "?" + queryString);

        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = config.hmacSHA512(secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = payUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public String returnUrl(HttpServletRequest request) {
        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
            String redirectUrl = "http://localhost:3000/payment-success";
            return redirectUrl;
        } else {
            String redirectUrl = "http://localhost:3000/payment-failure";
            return redirectUrl;
        }
    }
}

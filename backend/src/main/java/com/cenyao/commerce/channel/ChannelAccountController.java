package com.cenyao.commerce.channel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 渠道账号查询骨架（凭证密文字段不在后续详情接口明文返回）。
 */
@RestController
@RequestMapping("/api/v1/channel-accounts")
@RequiredArgsConstructor
public class ChannelAccountController {

    private final ChannelAccountMapper channelAccountMapper;

    @GetMapping
    public ApiResponse<List<ChannelAccount>> list() {
        List<ChannelAccount> list = channelAccountMapper.selectList(
                new LambdaQueryWrapper<ChannelAccount>().orderByDesc(ChannelAccount::getId));
        // 骨架阶段：列表不返回密文字段内容（置空）
        list.forEach(a -> {
            a.setPartnerIdCipher(null);
            a.setPartnerKeyCipher(null);
            a.setAccessTokenCipher(null);
            a.setRefreshTokenCipher(null);
        });
        return ApiResponse.ok(list);
    }
}

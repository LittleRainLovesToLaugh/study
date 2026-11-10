package com.cenyao.commerce.channel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenyao.commerce.channel.dto.CreateChannelAccountRequest;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 渠道账号：创建虾皮店铺（含站点/语言）与列表查询。
 */
@RestController
@RequestMapping("/api/v1/channel-accounts")
@RequiredArgsConstructor
public class ChannelAccountController {

    private final ChannelAccountMapper channelAccountMapper;
    private final ChannelAccountService channelAccountService;

    @GetMapping
    public ApiResponse<List<ChannelAccount>> list() {
        List<ChannelAccount> list = channelAccountMapper.selectList(
                new LambdaQueryWrapper<ChannelAccount>().orderByDesc(ChannelAccount::getId));
        list.forEach(a -> {
            a.setPartnerIdCipher(null);
            a.setPartnerKeyCipher(null);
            a.setAccessTokenCipher(null);
            a.setRefreshTokenCipher(null);
        });
        return ApiResponse.ok(list);
    }

    @PostMapping
    public ApiResponse<ChannelAccount> create(@Valid @RequestBody CreateChannelAccountRequest request) {
        return ApiResponse.ok(channelAccountService.create(request));
    }
}

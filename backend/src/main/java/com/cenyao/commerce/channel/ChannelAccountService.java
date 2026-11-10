package com.cenyao.commerce.channel;

import com.cenyao.commerce.channel.dto.CreateChannelAccountRequest;
import com.cenyao.commerce.channel.entity.ChannelAccount;
import com.cenyao.commerce.channel.mapper.ChannelAccountMapper;
import com.cenyao.commerce.crypto.CredentialEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 渠道账号服务（一期仅虾皮）。
 */
@Service
@RequiredArgsConstructor
public class ChannelAccountService {

    private final ChannelAccountMapper channelAccountMapper;
    private final CredentialEncryptor credentialEncryptor;

    public ChannelAccount create(CreateChannelAccountRequest request) {
        ChannelAccount account = new ChannelAccount();
        account.setChannelCode(ShopeeChannelAdapter.CODE);
        account.setShopName(request.getShopName());
        account.setShopId(request.getShopId());
        account.setSiteRegion(request.getSiteRegion().trim().toUpperCase());
        account.setTargetLanguage(request.getTargetLanguage().trim());
        account.setStatus(1);
        if (request.getPartnerId() != null && !request.getPartnerId().isBlank()) {
            account.setPartnerIdCipher(credentialEncryptor.encrypt(request.getPartnerId()));
        }
        if (request.getPartnerKey() != null && !request.getPartnerKey().isBlank()) {
            account.setPartnerKeyCipher(credentialEncryptor.encrypt(request.getPartnerKey()));
        }
        if (request.getAccessToken() != null && !request.getAccessToken().isBlank()) {
            account.setAccessTokenCipher(credentialEncryptor.encrypt(request.getAccessToken()));
        }
        channelAccountMapper.insert(account);
        // 不回传密文
        account.setPartnerIdCipher(null);
        account.setPartnerKeyCipher(null);
        account.setAccessTokenCipher(null);
        account.setRefreshTokenCipher(null);
        return account;
    }
}

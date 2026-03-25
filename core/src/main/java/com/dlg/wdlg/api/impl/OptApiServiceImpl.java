package com.dlg.wdlg.api.impl;

import com.dlg.wdlg.api.OptApi;
import com.dlg.wdlg.comm.UserCardStateEnum;
import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.pojos.CardInfoPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptApiServiceImpl implements OptApi {

    @Override
    public CardInfoPojo getNextCard() {
        return null;
    }

    @Override
    public void optCard(Long cardId, UserCardStateEnum statge) {

    }

}

package com.dlg.wdlg.dto;

import com.dlg.wdlg.entity.CardEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import com.dlg.wdlg.entity.WordEntity;
import lombok.Data;

@Data
public class UserWordCardMemoryDto {

    private UserCardInfoEntity  userCardInfoEntity;

    private CardEntity cardEntity;

    private WordEntity wordEntity;



}

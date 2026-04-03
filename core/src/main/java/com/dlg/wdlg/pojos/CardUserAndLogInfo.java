package com.dlg.wdlg.pojos;

import com.dlg.wdlg.entity.CardMemoryLogEntity;
import com.dlg.wdlg.entity.UserCardInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CardUserAndLogInfo {

    UserCardInfoEntity userCardInfoEntity;

    CardMemoryLogEntity memoryLogEntity;

}

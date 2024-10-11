package com.code.BTS.utils.dto.request;

import com.code.BTS.entity.CheckList;
import com.code.BTS.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@Builder
public class CheckListRequest {
    private String name;

    public CheckList convert(){
        return CheckList.builder()
                .name(name)
                .build();
    }
}

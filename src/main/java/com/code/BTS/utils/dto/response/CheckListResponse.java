package com.code.BTS.utils.dto.response;

import com.code.BTS.entity.CheckList;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CheckListResponse {

    private Long id;
    private String name;

    public static CheckListResponse fromCheckList(CheckList checkList){
        return CheckListResponse.builder()
                .id(checkList.getId())
                .name(checkList.getName())
                .build();
    }
}

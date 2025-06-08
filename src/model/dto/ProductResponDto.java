package model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ProductResponDto(
        String pName,
        Float price,
        Integer qty,
        boolean isDeleted,
        String pUuid

) {

}

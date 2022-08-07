package DTO;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class HistoryDto {
    private int id;
    private double LAT ;
    private double LNT;
    private String DATE;
}

package website.marcioheleno.ajax_api.resources.exceptions;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandartError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}

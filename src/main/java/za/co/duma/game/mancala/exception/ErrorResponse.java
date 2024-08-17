package za.co.duma.game.mancala.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String message;
    private BigDecimal status;
    private String error;
}

package billing;

import com.phonecompany.billing.Bill;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillTest {
    @Test
    void testCalculateBill() throws IOException {
        Path filePath = Path.of("src/test/resources/mockup.csv");
        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        var billPrice = new Bill().calculate(content);

        assertEquals(billPrice, BigDecimal.valueOf(132.7));
    }
}
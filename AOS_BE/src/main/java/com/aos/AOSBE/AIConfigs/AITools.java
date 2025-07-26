package com.aos.AOSBE.AIConfigs;

import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.google.gson.Gson;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class AITools {
    @Tool(description = "Get the current date and time in the user's timezone")
//    tooparam thong tin bo sung
    String getCurrentDateTime(@ToolParam(description = "Time in ISO-8601 format") String time) {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    @Tool(description = "Set a user alarm for the given time, provided in ISO-8601 format")
    void setAlarm(String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }
    @Tool(description = "Phân tích xu hướng của sản phẩm và dự đoán nhu cầu trong 30 ngày tới")
    public String analyzeProductTrend(ProductItemsDTOS productItemsDTOS) {
                return """
        Bạn là chuyên gia phân tích dữ liệu bán lẻ và marketing chiến lược.
        
        Dưới đây là dữ liệu chi tiết về một sản phẩm cụ thể của doanh nghiệp. Hãy dựa trên các chỉ số để:
        1. **Đánh giá xu hướng hiệu suất sản phẩm trong 30 ngày gần nhất**: tăng/giảm về doanh số, mối liên hệ giữa tồn kho và tốc độ bán, ảnh hưởng của khuyến mãi nếu có.
        2. **Dự đoán nhu cầu trong 30 ngày tới**: xét theo tốc độ bán, vòng đời sản phẩm, mức độ đánh giá từ người dùng, tỷ lệ đổi trả, và các chỉ số liên quan.
        3. **Phân tích độ hiệu quả về giá bán**: lợi nhuận biên (giá - cost), lịch sử thay đổi giá, khả năng tăng/giảm giá dựa trên dữ liệu và tâm lý thị trường.
        4. **Đánh giá vai trò của sản phẩm trong các hoạt động marketing (combo, tặng quà)**: tần suất xuất hiện, mức độ phù hợp làm sản phẩm dẫn dụ (loss leader) hoặc gợi ý mua kèm.
        5. **Đề xuất chiến lược tối ưu**:
            - Có nên tăng/giảm giá?
            - Có nên tiếp tục đẩy sản phẩm trong combo/quà?
            - Nên tập trung vào kênh quảng bá nào? (nếu có thể gợi ý)
            - Có nên giữ mức tồn kho như hiện tại hay cần điều chỉnh?
        6. **Đánh giá tổng thể**: Sản phẩm có tiềm năng tăng trưởng không? Có cần thay đổi vị trí trong danh mục sản phẩm không?
        
        Yêu cầu:
        - Viết theo văn phong chuyên gia phân tích thị trường.
        - Không liệt kê lại dữ liệu đầu vào.
        - Nếu thông tin chưa đủ để kết luận, hãy nêu rõ giả định cần thêm.
        
        Dữ liệu sản phẩm:
        %s
        """.formatted(new Gson().toJson(productItemsDTOS));
            }
}

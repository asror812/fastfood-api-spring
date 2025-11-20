package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDTO;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.check.CheckRepository;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.order.OrderRepository;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.orderItem.entity.OrderItem;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.User;
import lombok.Getter;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Getter
public class BonusService extends GenericService<Bonus, BonusResponseDTO> {

    private final BonusRepository repository;
    private final Class<Bonus> entityClass = Bonus.class;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CheckRepository checkRepository;
    private final BonusMapper mapper;

    public BonusService(BonusRepository repository,
            OrderRepository orderRepository, UserRepository userRepository, CheckRepository checkRepository,
            BonusMapper mapper) {
        super(mapper);
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.checkRepository = checkRepository;
        this.mapper = null;
    }

    public List<Bonus> getAvailableOrderBonuses(User user, Order order) {
        List<Bonus> appliableBonuses = new ArrayList<>();

        for (Bonus bonus : repository.findAllActiveAndValid()) {
            if (isBonusApplicable(bonus, user, order)) {
                appliableBonuses.add(bonus);
            }
        }

        return appliableBonuses;
    }

    private boolean isBonusApplicable(Bonus bonus, User user, Order order) {

        return switch (bonus.getCondition().getConditionType()) {
            case HOLIDAY_BONUS -> true;
            case FIRST_TIME_PURCHASE -> checkRepository.getPurchasesCountOfUser(user.getId()) == 0;
            case TOTAL_SPENT -> checkOrderPrice(order, bonus.getCondition().getValue());
            case QUANTITY -> isProductBonusApplicable(bonus, order);
            default -> false;
        };

    }

    public boolean checkOrderPrice(Order order, String conditionValue) {
        BigDecimal value = new BigDecimal(conditionValue);
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem orderItem : order.getOrderItems()) {
            totalPrice = totalPrice.add(
                    orderItem.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        return totalPrice.compareTo(value) >= 0;
    }

    private boolean isProductBonusApplicable(Bonus bonus, Order order) {
        for (BonusProductLink link : bonus.getBonusProductLinks()) {
            for (OrderItem item : order.getOrderItems()) {
                if (link.getProduct().getId().equals(item.getProduct().getId())
                        && item.getQuantity() >= link.getQuantity()) {
                    return true;
                }
            }
        }
        return false;
    }

}

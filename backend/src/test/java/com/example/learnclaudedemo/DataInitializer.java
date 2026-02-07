package com.example.learnclaudedemo;

import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DataInitializer {

    @Autowired
    private PetRepository petRepository;

    @Test
    @Transactional
    @Commit
    public void initTestData() {
        List<Pet> pets = new ArrayList<>();

        String[] names = { "小强", "大黄", "皮皮", "球球", "豆豆", "点点", "旺财", "年糕", "糯米", "雪球" };
        String[] types = { "狗", "狗", "猫", "猫", "兔子", "狗", "狗", "猫", "猫", "狗" };
        String[] breeds = { "哈士奇", "金毛", "英短", "暹罗", "垂耳兔", "柯基", "柴犬", "布偶", "加菲", "萨摩耶" };

        for (int i = 0; i < 10; i++) {
            Pet pet = new Pet();
            pet.setUserId(100L + i);
            pet.setPetName(names[i]);
            pet.setPetType(types[i]);
            pet.setBreed(breeds[i]);
            pet.setGender(i % 2 + 1);
            pet.setBirthDate(LocalDate.now().minusMonths(6 + i));
            pet.setWeight(new BigDecimal("3.5").add(new BigDecimal(i).multiply(new BigDecimal("0.5"))));
            pet.setStatus(0);
            pet.setDescription("这是一只非常可爱的" + breeds[i]);
            pet.setSterilized(i % 2);
            pet.setVaccinated(1);
            pets.add(pet);
        }

        petRepository.saveAll(pets);
        System.out.println("成功初始化 10 条宠物测试数据！");
    }
}

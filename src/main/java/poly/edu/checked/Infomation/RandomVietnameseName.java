package poly.edu.checked.Infomation;

import java.util.Random;

public class RandomVietnameseName {
    private static final String[] streetPrefixes = {"Đường", "Phố", "Ngõ", "Hẻm"};
    private static final String[] streetNames = {"Lê Lợi", "Nguyễn Huệ", "Trần Hưng Đạo", "Phan Chu Trinh", "Hồ Chí Minh", "Bạch Đằng", "Trường Chinh", "Lý Thường Kiệt", "Đinh Tiên Hoàng", "Quang Trung"};
    private static final String[] streetSuffixes = {"", "Số 1", "Số 2", "Số 3", "Số 4", "Số 5"};

    private static final String[] cities = {"Hanoi", "Ho Chi Minh City", "Da Nang", "Hue", "Nha Trang", "Can Tho", "Hai Phong", "Vung Tau", "Phan Thiet", "Quy Nhon"};
    
    
    public static String generateRandomCity() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(cities.length);

        return cities[randomIndex];
    }
    public static String generateRandomPhoneNumber() {
        Random rand = new Random();
        StringBuilder phoneNumber = new StringBuilder("+84");

        // Tạo 9 số ngẫu nhiên
        for (int i = 0; i < 9; i++) {
            int digit = rand.nextInt(10); // Số ngẫu nhiên từ 0 đến 9
            phoneNumber.append(digit);
        }

        return phoneNumber.toString();
    }
    public static String generateRandomStreetName() {
        Random rand = new Random();
        String prefix = streetPrefixes[rand.nextInt(streetPrefixes.length)];
        String name = streetNames[rand.nextInt(streetNames.length)];
        String suffix = streetSuffixes[rand.nextInt(streetSuffixes.length)];

        return prefix + " " + name + " " + suffix;
    }
    
    public static String generateRandomPostcode() {
        Random rand = new Random();
        int postcodeLength = 6; // Độ dài của mã bưu điện (có thể thay đổi tùy ý)

        StringBuilder postcode = new StringBuilder();

        for (int i = 0; i < postcodeLength; i++) {
            int digit = rand.nextInt(10); // Số ngẫu nhiên từ 0 đến 9
            postcode.append(digit);
        }

        return postcode.toString();
    }
    public static String getRandomFirstName() {
        String[] firstNames = {"Trần", "Nguyễn", "Lê", "Phạm", "Hoàng", "Đỗ", "Ngô", "Vũ", "Bùi", "Hà", "Đặng"};
        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)];
    }

    public static String getRandomLastName() {
        String[] lastNames = {"Hằng", "Dũng", "Phúc", "Tú", "Nga", "Sơn", "Linh", "Chí", "Quang", "Giang"};
        Random random = new Random();
        return lastNames[random.nextInt(lastNames.length)];
    }
    
    public static String getRandomFirstNameInter() {
        String[] firstNames = {"Join", "David", "Hilda", "Andra", "Doe", "Tauras", "Gabrielius", "Miroslavas", "Fedoras", "Maria", "Rimantas"};
        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)];
    }
    
    public static String getRandomLastNameInter() {
        String[] lastNames = {"Valia", "Vaidis", "Vasiliauskas", "Justas", "Lapinskas", "Beata", "Danielius", "Gulbinas", "Elvyra", "Dana"};
        Random random = new Random();
        return lastNames[random.nextInt(lastNames.length)];
    }
}

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Create a new client
        Client client1 = new Client(
            101, 
            "Ahmad", 
            "Samandar", 
            LocalDate.of(2003, 5, 14),
            "ahmad@example.com",
            "6131234567",
            22
        );

        // 2️⃣ Set category and reward account
        client1.setCategory(ClientCategory.ADULT);

        client1.getClientInfo();

        // 5️⃣ Test total balance
        System.out.println("\nTotal Balance: $" + client1.getTotalBalance());

        // 6️⃣ Update some client info
        client1.updateContactInfo("Ahmad Naween", null, null, "newemail@example.com", "6137654321", 23);

        System.out.println("\n--- After Update ---");
        client1.getClientInfo();

 
        System.out.println("\n--- After Removing One Account ---");
        client1.getClientInfo();
    }
}

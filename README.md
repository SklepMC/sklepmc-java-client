# SklepMC Java API
Implementacja publicznego API SklepMC w Javie.

## Przykładowe użycie
```java
// dane uwierzytelniające
String shopId = "TWOJE-ID-SKLEPU";
String secret = "TWOJ-KLUCZ-PRYWATNY";

// tworzymy context (w większości przypadków należy go gdzieś zapisać)
ApiContext apiContext = new ApiContext(shopId, secret);
```

## Pobieranie podstawowych informacji
```java
// informacje o sklepie
ShopInfo shop = ShopInfo.get(apiContext);
System.out.println(shop);

// informacje o serwerze
int serverId = 1234;
ServerInfo server = ServerInfo.get(apiContext, serverId);
System.out.println(server);

// informacje o usłudze
int serviceId = 4321;
ServiceInfo service = ServiceInfo.get(apiContext, serviceId);
System.out.println(service);

// informacje o transakcji
String transactionId = "SMC-ABCDFGHI";
TransactionInfo transaction = TransactionInfo.get(apiContext, transactionId);
System.out.println(transaction);
```

## Pobieranie i wykonywanie transakcji
```java
ExecutionInfo execution = ExecutionInfo.get(apiContext, serverId);
List<ExecutionTaskInfo> executionTasks = executionInfo.getExecutionTasks();

for (ExecutionTaskInfo executionTask : executionTasks) {

    List<ExecutionCommandInfo> commands = executionTask.getCommands();
    String transactionId = executionTask.getTransactionId();
    boolean requireOnline = executionTask.isRequireOnline();

    // zmieniamy status transakcji na zakończony (COMPLETED)
    boolean updated;
    try {
        updated = TransactionInfo.updateStatus(apiContext, transactionId, TransactionInfo.TransactionStatus.COMPLETED.name());
    } catch (ApiException exception) {
        ApiError apiError = exception.getApiError();
        System.out.println("Błąd API: " + apiError.getType() + ", " + apiError.getMessage());
        continue;
    }

    // sprawdzamy czy wykonano pomyślnie zmianę, aby uniknąć wielokrotnych wykonań
    if (!updated) {
        System.out.println("Nie udało się zmienić statusu transakcji: " + transactionId);
        continue;
    }

    // wykonujemy komendy transakcji
    for (ExecutionCommandInfo command : commands) {

        // komenda wymaga aby cel (gracz którego dotyczy) był online, 
        // sprawdzamy czy gracz jest na serwerze
        if (requireOnline) {
            Player playerExact = Bukkit.getPlayerExact(command.getTarget());
            if (playerExact == null) {
                continue;
            }
        }

        String commandText = command.getText();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandText);
    }
}
```
# SklepMC Java API
Java implementation of public SklepMC API.

## Example usage
```java
// credentials
String shopId = "YOUR-SHOP-ID";
String secret = "YOUR-SECRET";

// create context (in most situations you probably want to store it somewhere)
ApiContext apiContext = new ApiContext(shopId, secret);
```

## Receiving basic data
```java
// receive basic shop info
ShopInfo shop = ShopInfo.get(apiContext);
System.out.println(shop);

// receive server info
int serverId = 1234;
ServerInfo server = ServerInfo.get(apiContext, serverId);
System.out.println(server);

// receive service info
int serviceId = 4321;
ServiceInfo service = ServiceInfo.get(apiContext, serviceId);
System.out.println(service);

// receive transaction info
String transactionId = "SMC-ABCDFGHI";
TransactionInfo transaction = TransactionInfo.get(apiContext, transactionId);
System.out.println(transaction);
```

## Receiving and executing transactions
```java
ExecutionInfo execution = ExecutionInfo.get(apiContext, serverId);
List<ExecutionTaskInfo> executionTasks = executionInfo.getExecutionTasks();

for (ExecutionTaskInfo executionTask : executionTasks) {

    List<ExecutionCommandInfo> commands = executionTask.getCommands();
    String transactionId = executionTask.getTransactionId();
    boolean requireOnline = executionTask.isRequireOnline();

    // change transaction status to COMPLETED
    boolean updated;
    try {
        updated = TransactionInfo.updateStatus(apiContext, transactionId, TransactionInfo.TransactionStatus.COMPLETED.name());
    } catch (ApiException exception) {
        ApiError apiError = exception.getApiError();
        System.out.println("API error: " + apiError.getType() + ", " + apiError.getMessage());
        continue;
    }

    // handle failure to prevent multiple executions
    if (!updated) {
        System.out.println("Failed to change transaction status: " + transactionId);
        continue;
    }

    // run commands
    for (ExecutionCommandInfo command : commands) {

        // execution requires target to be online, skipping
        if (requireOnline) {
            Player playerExact = Bukkit.getPlayerExact(command.getTarget());
            if (playerExact == null) {
                continue;
            }
        }

        String commandText = command.getText();
        this.dispatchCommand(commandText);
    }
}
```
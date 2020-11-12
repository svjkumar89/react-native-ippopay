# react-native-ippopay
(Currently supporting Android only)
## Usage
```javascript
import Ippopay from 'react-native-ippopay';
```
## Initiate
To initiate the instance use the following code in your componentDidMount or useEffect if you are using functional component
```javascript
Ippopay.init(YOUR_PUBLIC_KEY)
```

## Add Listioner

```javascript
useEffect(() => {
    Ippopay.addListener(Ippopay.Events.IPPOPAY_RESPONSE, paymentResp)

    return () => {
        Ippopay.removeListener(Ippopay.Events.IPPOPAY_RESPONSE, paymentResp)
    }
}, [])
```
```javascript
const paymentResp = (data) => {
    console.log(data)
}
```

## To make payment

You need to get orderId from your server. (Use createorder api for this)

```javascript
Ippopay.makePayment({
    ORDER_ID: orderId,
    CUSTOMER_NAME: "Name",
    EMAIL: "EMAIL",
    TXN_AMOUNT: 100.00,
    MOBILE_NO: "xxxxxx000",
    DESCRIPTION: "TEST"
})
```  
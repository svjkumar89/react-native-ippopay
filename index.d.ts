interface IppoPayPaymentDetails {
    ORDER_ID: string;
    CUSTOMER_NAME: string;
    TXN_AMOUNT: number;
    EMAIL?: string;
    MOBILE_NO?: string;
    DESCRIPTION?: string;
}

interface IppoPayEvents {
    IPPOPAY_RESPONSE: 'IppoPayResponse';
}

interface IppopayResponseIos {
    status: 'SUCCESS' | 'FAILURE' | 'CANCELLED';
    transactionId: string;
    error: string;
}


export const Events: IppoPayEvents;

export function init(publicKey: string): void;

export function makePayment(details: IppoPayPaymentDetails): void;

export function addListener(event: string, handler: (response: IppopayResponseIos) => void): void;

export function removeListener(event: string, handler: (response: IppopayResponseIos) => void): void;
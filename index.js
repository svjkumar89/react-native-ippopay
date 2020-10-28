import { NativeModules, NativeEventEmitter, DeviceEmitter } from 'react-native';

const Events = {
    IPPOPAY_RESPONSE: 'IppoPayResponse'
};
const EventEmitter = NativeModules.Ippopay ? new NativeEventEmitter(NativeModules.Ippopay) : DeviceEmitter;

function init(publicKey) {
    NativeModules.Ippopay.init(publicKey);
}

function makePayment(details) {
    NativeModules.Ippopay.makePayment(details);
}

function addListener(eventName, handler) {
    if (EventEmitter) {
        EventEmitter.addListener(eventName, handler);
    }
}

function removeListener(eventName, handler) {
    if (EventEmitter) {
        EventEmitter.removeListener(eventName, handler);
    }
}

export default {
    Events,
    addListener,
    removeListener,
    makePayment,
    init
};

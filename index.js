import { NativeModules } from 'react-native';

const { Wol } = NativeModules;

const RNWol = {
    send: (ip, mac, cb) => {
        if(cb) {
            Wol.send(ip, mac, cb)
        } else {
            Wol.send(ip, mac, () => null)
        }
    }
}

export default RNWol;

import {atom} from "recoil";

export const highligthState = atom<string | undefined>({
    key: 'highligthState', // unique ID (with respect to other atoms/selectors)
    default: undefined, // default value (aka initial value)
});
import {createContext} from "react";

const UserContext = createContext({user: null, token: null, test: 'a'});

export { UserContext }
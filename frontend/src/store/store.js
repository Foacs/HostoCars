import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';

import { composeWithDevTools } from 'redux-devtools-extension';

import { navigationReducer, testReducer } from 'store';

const store = createStore(
    combineReducers({
        navigation: navigationReducer,
        test: testReducer
    }),
    composeWithDevTools(applyMiddleware(thunk))
);

export default store;

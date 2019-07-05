import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';

import { composeWithDevTools } from 'redux-devtools-extension';

import { carsReducer, navigationReducer } from 'store';

const store = createStore(
    combineReducers({
        cars: carsReducer,
        navigation: navigationReducer
    }),
    composeWithDevTools(applyMiddleware(thunk))
);

export default store;

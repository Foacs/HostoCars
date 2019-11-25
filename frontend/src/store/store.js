import { applyMiddleware, combineReducers, createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';

import { carsReducer, navigationReducer } from 'store';

/**
 * Combine all the application reducers.
 */
const store = createStore(combineReducers({
    cars: carsReducer,
    navigation: navigationReducer
}), composeWithDevTools(applyMiddleware(thunk)));

export default store;

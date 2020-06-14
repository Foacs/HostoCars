import { applyMiddleware, combineReducers, createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import { carsReducer, interventionsReducer, navigationReducer, notificationReducer } from 'store';

/**
 * Combines all the application reducers.
 *
 * @type {*}
 */
const store = createStore(combineReducers({
    cars: carsReducer,
    navigation: navigationReducer,
    notifications: notificationReducer
}), composeWithDevTools(applyMiddleware(thunk)));

export default store;

import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Fab, Grid } from '@material-ui/core';
import { AddRounded as CreateIcon, RefreshRounded as RefreshIcon } from '@material-ui/icons';

import { createCarAction, getCarsAction, updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction } from 'actions';
import { CarCard, ErrorPanel, LoadingPanel, Page } from 'components';
import { CreateCarModal } from 'modals';
import { CarPropType } from 'resources';

import './CarsOverviewPage.scss';

/**
 * The cars overview page component.
 *
 * @param {object[]} cars
 *    The list of all the cars
 * @param {func} createCar
 *     The {@link createCarAction} action
 * @param {func} getCars
 *     The {@link getCarsAction} action
 * @param {boolean} isInError
 *    If the cars loading is in error
 * @param {boolean} isLoading
 *    If the cars loading is in progress
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateMenuItems
 *     The {@link updateMenuItemsAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class CarsOverviewPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = { isCreateCarModalOpen: false };

        // Binds the local methods
        this.onCloseCreateCarModal = this.onCloseCreateCarModal.bind(this);
        this.onOpenCreateCarModal = this.onOpenCreateCarModal.bind(this);
        this.onValidateCreateCarModal = this.onValidateCreateCarModal.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const {
            getCars,
            updateCurrentPage,
            updateMenuItems,
            updateSelectedMenuIndex
        } = this.props;

        updateCurrentPage('Voitures', []);
        updateMenuItems([ {
            icon: <RefreshIcon />,
            label: 'Rafraîchir',
            onClick: getCars
        } ]);
        updateSelectedMenuIndex(0);
    }

    /**
     * Handles the 'Create car' modal close action.
     */
    onCloseCreateCarModal() {
        this.setState({ isCreateCarModalOpen: false });
    }

    /**
     * Handles the 'Create car' button click action.
     */
    onOpenCreateCarModal() {
        this.setState({ isCreateCarModalOpen: true });
    }

    /**
     * Handles the 'Create car' modal validate action.
     *
     * @param {object} car
     *     The car to create
     */
    onValidateCreateCarModal(car) {
        const { createCar } = this.props;

        createCar(car);
    }

    /**
     * Render method.
     */
    render() {
        const {
            cars,
            isInError,
            isLoading
        } = this.props;
        const { isCreateCarModalOpen } = this.state;

        let content;
        if (isInError) {
            // If the cars failed to be loaded, displays the error panel
            content = (<ErrorPanel className='ErrorPanel' />);
        } else if (isLoading) {
            // If the cars are being loaded, displays the loading panel
            content = (<LoadingPanel className='LoadingPanel' />);
        } else {
            // If the cars have been loaded, displays the normal page content
            content = (<Fragment>
                <Grid alignItems='flex-start' className="CarsGrid" container justify='flex-start' spacing={4}>
                    {cars.map(car => <Grid item key={car.registration} lg={4} md={6} sm={12} xl={3} xs={12}>
                        <CarCard car={car} className='CarCard' />
                    </Grid>)}
                </Grid>

                <Fab className='CreateCarButton' color='primary' onClick={this.onOpenCreateCarModal} size="medium" variant='round'>
                    <CreateIcon className='CreateCarIcon' />
                </Fab>

                <CreateCarModal onClose={this.onCloseCreateCarModal} open={isCreateCarModalOpen} onValidate={this.onValidateCreateCarModal}
                                registrations={cars.map(car => car.registration)} serialNumbers={cars.map(car => car.serialNumber)} />
            </Fragment>);
        }

        return (<Page id='CarsOverviewPage'>
            {content}
        </Page>);
    }
}

const mapStateToProps = (state) => ({
    cars: state.cars.cars,
    isInError: state.cars.isGetInError,
    isLoading: state.cars.isGetInProgress
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    createCar: createCarAction,
    getCars: getCarsAction,
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

CarsOverviewPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    createCar: PropTypes.func.isRequired,
    getCars: PropTypes.func.isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarsOverviewPage);

import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, IconButton, Typography } from '@material-ui/core';
import { ClearRounded as ClearIcon } from '@material-ui/icons';

import { changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction, getInterventionsAction } from 'actions';
import { ErrorPanel, InterventionPreview, LoadingPanel } from 'components';
import { InterventionPropType } from '../../../resources';

import './InterventionsOverviewPage.scss';

/**
 * The interventions overview page component.
 *
 * @param {func} changeCurrentPage
 *     The {@link changeCurrentPageAction} action
 * @param {func} changeSelectedMenuIndex
 *     The {@link changeSelectedMenuIndexAction} action
 * @param {func} getCars
 *     The {@link getCarsAction} action
 * @param {func} getInterventions
 *     The {@link getInterventionsAction} action
 * @param {object[]} interventions
 *    The list of all the interventions
 * @param {boolean} isInError
 *    If the interventions loading is in error
 * @param {boolean} isLoading
 *    If the interventions loading is in progress
 * @param {string} sortedBy
 *    The sorting clause
 *
 * @class
 */
class InterventionsOverviewPage extends PureComponent {
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
        this.state = { expandedInterventionIndex: false };

        // Binds the local method
        this.onInterventionPreviewClick = this.onInterventionPreviewClick.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars, getInterventions } = this.props;

        changeCurrentPage('Interventions', []);
        changeSelectedMenuIndex(1);

        getCars();
        getInterventions();
    }

    /**
     * Handles an intervention preview panel click action.
     * <br/>
     * This method is used to create an accordion effect on the interventions previews.
     *
     * @param index
     *    The index of the expanded intervention preview panel
     */
    onInterventionPreviewClick(index) {
        this.setState({ expandedInterventionIndex: this.state.expandedInterventionIndex === index ? false : index });
    }

    /**
     * Render method.
     */
    render() {
        const { cars, interventions, isInError, isLoading } = this.props;
        const { expandedInterventionIndex } = this.state;

        let content;
        if (isInError) {
            // If the interventions or the cars failed to be loaded, displays the error panel
            content = (<ErrorPanel className='ErrorPanel' />);
        } else if (isLoading) {
            // If the interventions or the cars are being loaded, displays the loading panel
            content = (<LoadingPanel className='LoadingPanel' />);
        } else {
            // If the interventions and the cars have been loaded, displays the page normal content
            content = (<Fragment>
                <Grid container spacing={4}>
                    <Grid item xs={4}>
                        <ExpansionPanel className='FilterPanel' expanded={true}>
                            <ExpansionPanelSummary className='Header'>
                                <Typography className='Title' color='primary' variant='h6'>Filtres</Typography>

                                <IconButton className='ClearButton' color='primary' onClick={this.onClearFilters}>
                                    <ClearIcon />
                                </IconButton>
                            </ExpansionPanelSummary>

                            <ExpansionPanelDetails />
                        </ExpansionPanel>
                    </Grid>

                    <Grid item xs={8}>
                        {interventions.map((intervention, index) => (
                                <InterventionPreview carRegistration={cars.find(car => car.id === intervention.carId).registration}
                                                     intervention={intervention} expanded={expandedInterventionIndex === index} key={index}
                                                     onClick={() => this.onInterventionPreviewClick(index)} />))}
                    </Grid>
                </Grid>
            </Fragment>);
        }

        return (<Box id='InterventionsOverviewPage'>
            {content}
        </Box>);
    }
}

const mapStateToProps = (state) => ({
    cars: state.cars.cars,
    interventions: state.interventions.interventions,
    isInError: state.interventions.isGetAllInError || state.cars.isGetAllInError,
    isLoading: state.interventions.isGetAllInProgress || state.cars.isGetAllInProgress
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    getCars: getCarsAction,
    getInterventions: getInterventionsAction
}, dispatch);

InterventionsOverviewPage.propTypes = {
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    getCars: PropTypes.func.isRequired,
    getInterventions: PropTypes.func.isRequired,
    interventions: PropTypes.arrayOf(InterventionPropType).isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(InterventionsOverviewPage);

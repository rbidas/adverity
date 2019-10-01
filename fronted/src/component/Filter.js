import React from "react";
import {Multiselect} from "react-widgets";

import 'react-widgets/dist/css/react-widgets.css';
import './Filter.css'

const CONFIG_URL = "http://localhost:8080/config";

export default class Filter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            datasource: [],
            campaign: [],
            datasourceSelected: [],
            campaignSelected: [],
            selectedOption: null,
        }
    }
    onChange = {}

    componentDidMount() {
        fetch(CONFIG_URL)
            .then(res => res.json())
            .then(data => {
                this.setState({datasource: this.convertToMultiSelectOptions(data.datasource)});
                this.setState({campaign: this.convertToMultiSelectOptions(data.campaign)});
            });
    }

    convertToMultiSelectOptions = arrayToConvert => arrayToConvert.map((value, index) => ({name: value, id: index}));

    apply = () => {
        this.props.emitter.emit('apply',  this.state.datasourceSelected, this.state.campaignSelected)
    };


    render() {
        return (
            <div className="filter-panel">
                <div className="filter-title"> Filter dimensions Values </div>
                <br/>
                <div>
                    DataSource:<br/>
                    <Multiselect
                        data={this.state.datasource}
                        valueField='id'
                        textField='name'
                        onChange={value => this.setState({datasourceSelected: value})}
                    />
                    Campaign:<br/>
                    <Multiselect
                        data={this.state.campaign}
                        valueField='id'
                        textField='name'
                        onChange={value => this.setState({campaignSelected: value})}
                    /><br/>
                    <button onClick={this.apply}>Apply</button>

                </div>
            </div>
        );
    }
}

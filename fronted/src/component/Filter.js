import React from "react";
import {Multiselect} from "react-widgets";

import 'react-widgets/dist/css/react-widgets.css';

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

    componentDidMount() {
        fetch(CONFIG_URL)
            .then(res => res.json())
            .then(data => {
                this.setState({datasource: this.convertToMultiSelectOptions(data.datasource)});
                this.setState({campaign: this.convertToMultiSelectOptions(data.campaign)});
            });
    }

    convertToMultiSelectOptions = arrayToConvert => {
        const options = [];
        arrayToConvert.forEach((value, index, array) => {
            options.push({name: value, id: index});
        });
        return options;
    };

    apply = () => {
        console.log("działa");
        console.log("campaignSelected");
        console.log(this.state.campaignSelected);
        console.log("datasourceSelected");
        console.log(this.state.datasourceSelected);
    };


    render() {
        return (
            <div className="filter-panel">
                Filter dimensions Values
                <div>
                    <button onClick={this.apply}>Apply</button>
                    <br/>
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
                    />

                </div>
            </div>
        );
    }
}

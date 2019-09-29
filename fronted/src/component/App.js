import React from 'react';
import './App.css';
import Filter from "./Filter";
import ChartBox from "./ChartBox";

export default class App extends React.Component {
    render() {
        return (
            <div className="component-app">
                <div className="component-button-panel">
                    <div>
                        <Filter name="filer" clickHandler={this.handleClick}/>
                        <ChartBox name="chart" clickHandler={this.handleClick}/>
                    </div>
                </div>
            </div>
        );
    }
};

import React from "react";
import Chart from "react-apexcharts";

const DATA_URL = "http://localhost:8080/data";

export default class ChartBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            options: {
                chart: {
                    height: 350,
                    id: "basic-bar",
                    type: "line"
                },
                xaxis: {
                    categories: [],
                    type: "datetime",
                    labels: {
                        datetimeFormatter: {
                            year: 'yyyy',
                            month: 'MMM \'yy',
                            day: 'dd MMM',
                            hour: 'HH:mm'
                        }
                    }
                },
                yaxis: [
                    {
                        axisTicks: {
                            show: true
                        },
                        title: {
                            text: "clicks"
                        },
                    },
                    {
                        axisTicks: {
                            show: true
                        },
                        opposite: true,
                        title: {
                            text: "Impressions"
                        }
                    }
                ]
            },
            colors: ["#FF1654", "#247BA0"],
            series: [
                {
                    name: "Clicks",
                    data: []
                },
                {
                    name: "Impressions",
                    data: []
                }
            ],

        };
    }

    componentDidMount() {
        fetch(DATA_URL)
            .then(res => res.json())
            .then(data => {
                var series = [];
                var clicks = [];
                var impressions = [];
                data.forEach((value, index, array) => {
                    var parts = value.date.split('.');
                    series.push(new Date(parts[2], parts[1], parts[0]).getTime());
                    clicks.push(value.clicks);
                    impressions.push(value.impressions)
                });
                this.setState({options: {xaxis: {categories: series}}});
                this.setState({
                    series: [
                        {
                            name: "Clicks",
                            data: clicks
                        },
                        {
                            name: "Impressions",
                            data: impressions
                        }
                    ]
                });
            });
    }

    render() {
        return (
            <div className="chart-panel">
                <div className="row">
                    <div className="mixed-chart">
                        <Chart
                            options={this.state.options}
                            series={this.state.series}
                            type="line"
                            width="1000"
                        />
                    </div>
                </div>
            </div>
        );
    }
}
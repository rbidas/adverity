import React from "react";
import Chart from "react-apexcharts";
import './ChartBox.css'
const DATA_URL = "http://localhost:8080/data";

export default class ChartBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            title: "",
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
                            text: "clicks",
                            style: {
                                fontSize: '20px',
                            },
                        },
                    },
                    {
                        axisTicks: {
                            show: true
                        },
                        opposite: true,
                        title: {
                            text: "Impressions",
                            style: {
                                fontSize: '20px',
                            },
                        }
                    }
                ],
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
    getQueryParam(list, queryParamName){
        return list.map(value => encodeURIComponent(queryParamName) + '=' + encodeURIComponent(value.name)).join("&");
    }

    componentDidMount() {
        this.props.emitter.addListener('apply', (datasource, campaign) => {
            const url = DATA_URL
                + "?"
                + this.getQueryParam(datasource, "datasource")
                + "&"
                + this.getQueryParam(campaign, "campaign");

            fetch(url)
                .then(res => res.json())
                .then(data => {
                    const chartData = this.convertResponseToChartData(data);
                    this.setState({
                        title: this.extractTitle(datasource, campaign),
                        options: {
                            xaxis: {
                                categories: chartData.series
                            }
                        },
                        series: [
                            {
                                name: "Clicks",
                                data: chartData.clicks
                            },
                            {
                                name: "Impressions",
                                data: chartData.impressions
                            }
                        ]
                    });
                });
        })
    }

    convertResponseToChartData(data) {
        return data.reduce((result, item, index, array) => {
            const parts = item.date.split('.');
            result.series.push(new Date(parts[2], parts[1], parts[0]).getTime());
            result.clicks.push(item.clicks);
            result.impressions.push(item.impressions);
            return result;
        }, {series: [], clicks: [], impressions: []});
    }

    extractTitle(datasource, campaign) {
        let title = datasource.length === 0 ?  "All Datasources" : "Datasource " + datasource.map(value => "\"" + value.name + "\"").join(" and ");
        title = title + ";";
        title = title + (campaign.length === 0 ?  "All Campaigns" : "Campaign " + campaign.map(value => "\"" + value.name + "\"").join(" and "));
        return title;
    }

    render() {
        return (
            <div className="chart-panel">
                <div className="chart-title"> {this.state.title}</div>
                <div className="mixed-chart">
                    <Chart
                        options={this.state.options}
                        series={this.state.series}
                        type="line"
                        width="1200"
                    />
                </div>
            </div>
        );
    }
}
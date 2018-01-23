import * as b from 'bobril'
import {create as h1} from "./h1";
import {create as navBar} from "./Unavbar"

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container-fluid";
        me.style = {
            backgroundColor: "#103F54", minHeight: 300

        };
        me.children = [
            {
                tag: "div",
                className: "container",
                style:{ backgroundColor: "white", minHeight: 100,  border: "2px solid black", width: "70%", marginTop: 50},
                children: [

                    h1({text: "Bonton Gallery"}),
                    navBar(),
                ]

            }
        ]
    }
});
import * as b from 'bobril'
import {create as header} from "../components/Uheader"
import {create as footer} from "../components/footer"

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    id: "Wrapper",
    render(ctx: IContext, me: b.IBobrilNode) {
        document.title = "Bonton Gallery" ;
        me.children = [
            {
                className: "d",
                children: [
                    header(),
                    me.data.activeRouteHandler(), // this must be as children, because it returns content of nested routes
                    footer(),
                ]
            },

        ];

    }
});
import * as b from 'bobril'

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container-fluid";
        me.style = {
            backgroundColor: "#103F54", fontSize: 15, minHeight: 100, textAlign: "Center", color: "white"
        };
        me.children = [
            {
                tag: "div",
                className: "container",
                style: {marginTop: 40},
                children: ["Bonton gallery © 2017-2018"]
            }
        ]
    }
});
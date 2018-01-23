import * as b from 'bobril'


export interface IData {
    text: string;
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container";
        me.style = { backgroundColor: "white", minHeight: 100,  border: "2px solid black", width: "70%", marginTop: 50

        };
        me.children = [
            h1({text: "Bonton Gallery"}),
            navBar(),
        ]
    }
});